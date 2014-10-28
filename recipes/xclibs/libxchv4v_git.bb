DESCRIPTION = "Haskell bindings to libv4v"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://../COPYING;md5=321bf41f280cf805086dd5a720b37785"
DEPENDS += "ghc-network libv4v libxchutils"
RDEPENDS_${PN} += "glibc-gconv-utf-32"

inherit xenclient ghc ghc-lib-common ghc-xc

SRC_URI = "${OPENXT_GIT_MIRROR}/xclibs.git;protocol=git;tag=${OPENXT_TAG}"
S = "${WORKDIR}/git/xchv4v"
