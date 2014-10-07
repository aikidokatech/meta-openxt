PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://login.selinux"

do_install_append() {
	install -m 0644 ${WORKDIR}/login.selinux ${D}${sysconfdir}/pam.d/login
}
