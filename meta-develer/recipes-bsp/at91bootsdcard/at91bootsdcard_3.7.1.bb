SUMMARY = "Initial bootstrap for AT91 ARM MPUs"
DESCRIPTION = " \
		at91bootstrap is the second-level bootloader for Atmel AT91  \
		SoCs. It provides a set of algorithms to manage the hardware \
		initialization and to download the main application (or a    \
		third-level bootloader) from specified boot media to         \
		main memory and start it.                                    \
	      "
AUTHOR = "Atmel Corporation"
HOMEPAGE = "http://www.at91.com/linux4sam/bin/view/Linux4SAM/AT91Bootstrap"
BUGTRACKER = "https://github.com/linux4sam/at91bootstrap/issues"
SECTION = "bootloaders"
LICENSE = "ATMEL"
LIC_FILES_CHKSUM = "file://main.c;endline=27;md5=42f86d2f6fd17d1221c5c651b487a07f"

inherit deploy

SRC_URI = " \
	https://github.com/linux4sam/at91bootstrap/archive/v${PV}.tar.gz \
	"

SRC_URI[md5sum] = "8030f57bd742b7300ef41c78d4a310e0"
SRC_URI[sha256sum] = "3f475b011e48acbd90181482b088be8b3f87cfe7ee452ce26c1bec22849d997d"

PACKAGE_ARCH = "${MACHINE_ARCH}"
EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

# Change source and build directories
S = "${WORKDIR}/at91bootstrap-${PV}"
B = "${S}"

do_configure() {
	oe_runmake sama5d4_xplainedsd_uboot_secure_defconfig
}

do_compile() {
	unset CFLAGS
	unset CPPFLAGS
	unset LDFLAGS
	oe_runmake
}

DEPLOYNAME = "at91bootstrap-sdcardboot-uboot-${PV}.bin"

do_deploy() {
	install -d ${DEPLOYDIR}
	install ${B}/binaries/at91bootstrap.bin ${DEPLOYDIR}/${DEPLOYNAME}

	cd ${DEPLOYDIR}

	# Create a symlink ready for file copy on SD card
	rm -f boot.bin BOOT.BIN
	ln -sf ${DEPLOYNAME} BOOT.BIN
}
addtask deploy before do_build after do_compile


COMPATIBLE_MACHINE_develboard = 'develboard'
