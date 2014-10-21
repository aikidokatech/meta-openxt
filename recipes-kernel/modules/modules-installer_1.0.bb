SECTION = "base"
DESCRIPTION = "Package providing /etc/modules file"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI = "file://modules"

do_install() {
	install -d ${D}${sysconfdir}
	install ${WORKDIR}/modules ${D}${sysconfdir}
}
