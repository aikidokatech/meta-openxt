# Part 2 of the XenClient host installer.
#
# This contains the logic to install or upgrade a specific version of
# XenClient. The resulting image is copied to the control.tar.bz2 file
# in the XenClient repository.

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe      \
                    file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

COMPATIBLE_MACHINE = "(openxt-dom0)"

export IMAGE_BASENAME = "xenclient-installer-part2-image"

PACKAGE_INSTALL = "xenclient-installer-part2"

IMAGE_BOOT = ""
IMAGE_FSTYPES = "tar.bz2"
IMAGE_INSTALL = ""
IMAGE_LINGUAS = ""
ONLINE_PACKAGE_MANAGEMENT = "none"

inherit image openxt
inherit xenclient-image-src-info
inherit xenclient-image-src-package
inherit xenclient-licences
require xenclient-version.inc

cleanup_after_rootfs () {
	mv ${IMAGE_ROOTFS}/etc/xenclient.conf ${IMAGE_ROOTFS}/config/;
	rm -rf ${IMAGE_ROOTFS}/dev;
	rm -rf ${IMAGE_ROOTFS}/etc;
	rm -rf ${IMAGE_ROOTFS}/usr;
}

do_ship() {
	mkdir -p "${OUT_DIR_RAW}"

        cp "${DEPLOY_DIR}/images/${MACHINE}/${PN}-${MACHINE}.tar.bz2" "${OUT_DIR_RAW}/control.tar.bz2"
}

addtask do_ship after do_rootfs before do_licences

IMAGE_PREPROCESS_COMMAND += " cleanup_after_rootfs; "

# prevent ldconfig from being run
LDCONFIGDEPEND = ""
