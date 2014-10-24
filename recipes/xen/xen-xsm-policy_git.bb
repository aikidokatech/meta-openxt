DESCRIPTION = "XSM Policy"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=4641e94ec96f98fabc56ff9cc48be14b"
DEPENDS += "checkpolicy-native"
PROVIDES = "xen-xsm-policy"

S = "${WORKDIR}/git"

SRC_URI = "${OPENXT_GIT_MIRROR}/xsm-policy.git;protocol=git;tag=${OPENXT_TAG}"

FILES_${PN} += "/etc/xen/refpolicy/policy/policy.24"

EXTRA_OEMAKE = " -j 1 "

# This package's make file needs to be adjusted to be more OE friendly
do_compile(){
	oe_runmake DESTDIR=${D} BINDIR=${STAGING_BINDIR_NATIVE}
}

do_install(){
	mkdir -p ${D}/etc/xen/xenrefpolicy/users/
	touch ${D}/etc/xen/xenrefpolicy/users/system.users
	touch ${D}/etc/xen/xenrefpolicy/users/local.users
	DESTDIR=${D} BINDIR=${STAGING_BINDIR_NATIVE} oe_runmake install
}

inherit xenclient
