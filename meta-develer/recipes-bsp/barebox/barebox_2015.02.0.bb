require recipes-bsp/barebox/barebox.inc

SRC_URI = " \
	http://barebox.org/download/${PN}-${PV}.tar.bz2;name=tar \
	${DEVELBOARD_GIT} \
	"

SRC_URI[tar.md5sum] = "854153097ed0c33086667d1132e7bb0d"
SRC_URI[tar.sha256sum] = "e103a69440e452841c50e140d82026bf0390453893475db675e1a0f05a3ef02c"

COMPATIBLE_MACHINE_develboard = "develboard"
