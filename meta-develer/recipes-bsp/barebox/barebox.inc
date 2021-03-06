DESCRIPTION = "Barebox - a bootloader that inherits the best of U-Boot and the Linux kernel"
HOMEPAGE = "http://www.barebox.org/"
SECTION = "bootloader"
PROVIDES = "virtual/bootloader"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=057bf9e50e1ca857d0eb97bfe4ba8e5d"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit kernel-arch
inherit deploy

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

BAREBOX_MACHINE ?= "${MACHINE}_defconfig"
BAREBOX_BINARY ?= "barebox-flash-image"
BAREBOX_IMAGE ?= "barebox-${MACHINE}-${PV}-${PR}.bin"
BAREBOXENV_BIN ?= "bareboxenv-${MACHINE}-${PV}-${PR}.bin"
BAREBOX_SYMLINK ?= "barebox-${MACHINE}.bin"
BAREBOXENV_SYMLINK ?= "bareboxenv-${MACHINE}.bin"

INSANE_SKIP_${PN} = "True"

do_pre_configure() {
	defconfig=${S}/../git/buildroot/board/develer/develboard/${PN}-${PV}.config

	if [ -f ${WORKDIR}/defconfig ]; then
		cp ${WORKDIR}/defconfig ${S}/.config
		oe_runmake oldconfig
	elif [ -f ${defconfig} ]; then
		cp ${defconfig} ${S}/.config
		oe_runmake oldconfig
	else
		oe_runmake ${BAREBOX_MACHINE}
	fi
}
addtask pre_configure before do_configure after do_patch

do_my_patch() {
	cd "${S}"
	patchdir=../git/buildroot/board/develer/develboard/${PN}-${PV}-patches
	cp ${patchdir}/${PN}-${PV}-*.patch patches

	for p in $(ls -A patches/*); do
		patch -p1 -N --dry-run -i ${p} | grep -q "patch detected.*Skipping patch" \
		|| patch -p1 -N < ${p}
	done
}
addtask my_patch after do_patch before do_pre_configure

do_compile() {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' Makefile
	fi
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	oe_runmake all
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install ${B}/${BAREBOX_BINARY} ${DEPLOYDIR}/${BAREBOX_IMAGE}

	cd ${DEPLOYDIR}
	rm -f ${BAREBOX_SYMLINK}
	ln -sf ${BAREBOX_IMAGE} ${BAREBOX_SYMLINK}
	rm -f u-boot.bin
	ln -sf ${BAREBOX_IMAGE} u-boot.bin

	install -d ${STAGING_BINDIR_NATIVE}
	cd ${B}
	install -m 755 scripts/mkimage ${STAGING_BINDIR_NATIVE}/
	install -m 755 scripts/bareboxenv ${STAGING_BINDIR_NATIVE}/
}
addtask deploy before do_build after do_compile

do_deploy_append() {
	ENVDIR=${B}/../git/buildroot/board/develer/develboard/${PN}-${PV}-env
	${B}/scripts/bareboxenv -s ${ENVDIR} ${B}/${BAREBOXENV_BIN}

	install -d ${DEPLOYDIR}
	install ${B}/${BAREBOXENV_BIN} ${DEPLOYDIR}/${BAREBOXENV_BIN}

	cd ${DEPLOYDIR}
	rm -f ${BAREBOXENV_SYMLINK}
	ln -sf ${BAREBOXENV_BIN} ${BAREBOXENV_SYMLINK}
	rm -f barebox.env
	ln -sf ${BAREBOXENV_BIN} barebox.env
}
