DESCRIPTION = "XenClient V4V kernel headers"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4641e94ec96f98fabc56ff9cc48be14b"

# TODO: The sole purpose of this recipe is to install a header file
# that is already installed by v4v-module.  With dizzy this is now
# a stop error when v4v-module tries to build.  Need to examine this 
# further.

PV = "0+git${SRCPV}"

SRCREV = "${AUTOREV}"
SRC_URI = "git://${OPENXT_GIT_MIRROR}/v4v.git;protocol=${OPENXT_GIT_PROTOCOL};branch=${OPENXT_BRANCH}"

S = "${WORKDIR}/git/v4v"

do_configure() {
:
}

do_compile() {
:
}

do_install(){
    install -d ${D}${includedir}/linux
    install ${S}/linux/v4v_dev.h ${D}${includedir}/linux/
}



