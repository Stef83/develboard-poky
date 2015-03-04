require recipes-bsp/barebox/barebox.inc

# TODO: handle defconfig and environment
#SRC_URI = "http://barebox.org/download/${PN}-${PV}.tar.bz2 \
#           file://defconfig"

SRC_URI = " \
	http://barebox.org/download/${PN}-${PV}.tar.bz2;name=tar \
	${DEVELBOARD_HTTP}/buildroot/board/develer/develboard/${PN}-${PV}.config;name=conf \
	${DEVELBOARD_HTTP}/buildroot/board/develer/develboard/${PN}-${PV}-patches/${PN}-${PV}-load-sd-card-environment-at-boot.patch;patch=1;name=patchenv \
	"

SRC_URI[tar.md5sum] = "854153097ed0c33086667d1132e7bb0d"
SRC_URI[tar.sha256sum] = "e103a69440e452841c50e140d82026bf0390453893475db675e1a0f05a3ef02c"

SRC_URI[conf.md5sum] = "4e2ca9746ef11e4677ff81f272483e6a"
SRC_URI[conf.sha256sum] = "5cca3e4e0522cc36cc5e8a2f944fecd391addd8a658056df8cf2af0b47717589"

SRC_URI[patchenv.md5sum] = "9d4a0995298fd99d7532bb8c510711bd"
SRC_URI[patchenv.sha256sum] = "c2a9cd63a6a28e560efc14d1182b942cb98364255cecd486b7ae3d930c0d5da3"

COMPATIBLE_MACHINE_develboard = "develboard"
