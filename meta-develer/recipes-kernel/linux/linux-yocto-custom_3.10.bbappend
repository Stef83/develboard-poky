KBRANCH = "linux-3.10-at91"
PV = "${LINUX_VERSION}+${SRCPV}"
PR = "r5"

# tag: linux4sam_4.6
SRCREV_develboard = "46f4253693b0ee8d25214e7ca0dde52e788ffe95"

SRC_URI_develboard = " \
	git://github.com/linux4sam/linux-at91.git;protocol=git;branch=${KBRANCH};nocheckout=1 \
	${DEVELBOARD_HTTP}/buildroot/board/develer/develboard/linux-linux4sam_4.6.config;name=defconfig \
	"

SRC_URI[defconfig.md5sum] = "4766847941dfcd39058be7348ea8d7bc"
SRC_URI[defconfig.sha256sum] = "a8fe1676b104c0685c4c1b7fbffd919277b767e31c429fef5c098775b1664dbe"

# workaround to make the kernel compile without a local defconfig file
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_develboard += "file://dummy.cfg"

do_configure_prepend() {
	# copy custom defconfig to the kernel build directory
	cp ${WORKDIR}/linux-linux4sam_4.6.config ${B}/.config
}

COMPATIBLE_MACHINE_develboard = "develboard"
