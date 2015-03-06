SRC_URI += " \
	${DEVELBOARD_HTTP}/buildroot/board/develer/develboard/at91_nand_header.sh \
	"

SRC_URI[md5sum] = "9927525f4038a3901bf47a4760708208"
SRC_URI[sha256sum] = "3fe85098fe82529e8dfaf3e2844bdd51ecd4a64024ac910e1ac8e3b0397747db"


DEPLOYNAME = "at91bootstrap-nandflashboot-uboot-${PV}.bin"

do_deploy() {
	install -d ${DEPLOYDIR}
	bash ${WORKDIR}/at91_nand_header.sh ${B}/binaries/at91bootstrap.bin ${DEPLOYDIR}/${DEPLOYNAME}

	cd ${DEPLOYDIR}

	# Create a symlink ready for file copy on NAND flash
	rm -f at91bootstrap.bin
	ln -sf ${DEPLOYNAME} at91bootstrap.bin
}

COMPATIBLE_MACHINE_develboard = 'develboard'
