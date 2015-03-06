DEPLOYNAME = "at91bootstrap-nandflashboot-uboot-${PV}.bin"

do_deploy() {
	install -d ${DEPLOYDIR}
	install ${B}/binaries/at91bootstrap.bin ${DEPLOYDIR}/${DEPLOYNAME}

	cd ${DEPLOYDIR}

	# Create a symlink ready for file copy on NAND flash
	rm -f at91bootstrap.bin
	ln -sf ${DEPLOYNAME} at91bootstrap.bin
}

COMPATIBLE_MACHINE_develboard = 'develboard'
