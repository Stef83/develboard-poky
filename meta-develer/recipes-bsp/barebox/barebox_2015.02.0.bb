require recipes-bsp/barebox/barebox.inc

# TODO: handle defconfig and environment
#SRC_URI = "http://barebox.org/download/${PN}-${PV}.tar.bz2 \
#           file://defconfig"

SRC_URI = "http://barebox.org/download/${PN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "854153097ed0c33086667d1132e7bb0d"
SRC_URI[sha256sum] = "e103a69440e452841c50e140d82026bf0390453893475db675e1a0f05a3ef02c"

COMPATIBLE_MACHINE_develboard = "develboard"
