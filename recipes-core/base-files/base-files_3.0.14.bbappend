PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://fstab.openxt-dom0 \
           file://fstab.openxt-ndvm \
           file://fstab.openxt-uivm \
           file://fstab.openxt-syncvm \
           file://licenses/BSD \
           file://licenses/GPL-3 \
           file://licenses/LGPL-2 \
           file://licenses/LGPL-2.1 \
           file://licenses/LGPL-3 \
           file://licenses/GFDL-1.2 \
           file://licenses/Artistic \
	"

do_install_append_openxt-dom0 () {
	install -m 0755 -d ${D}/config
	install -m 0755 -d ${D}/var/cores
	install -m 0755 -d ${D}/var/log
	install -m 0755 -d ${D}/storage
        install -m 0755 -d ${D}/var/lib/dbus
        install -m 0644 ${WORKDIR}/fstab.openxt-dom0 ${D}${sysconfdir}/fstab
	# some files are already provided by xenclient-initramfs-tpm-config-files
	#rm -f ${D}${sysconfdir}/nsswitch.conf
}

do_install_append_openxt-ndvm () {
        install -m 0755 -d ${D}/var/lib/dbus
        install -m 0644 ${WORKDIR}/fstab.openxt-ndvm ${D}${sysconfdir}/fstab
}

do_install_append_openxt-uivm () {
	install -m 0755 -d ${D}/var/lib/dbus
	install -m 0755 -d ${D}/etc/NetworkManager/system-connections
	install -m 0755 -d ${D}/root/.gconf
        install -m 0644 ${WORKDIR}/fstab.openxt-uivm ${D}${sysconfdir}/fstab
}

do_install_append_openxt-syncvm () {
	install -m 0755 -d ${D}/var/lib/dbus
        install -m 0644 ${WORKDIR}/fstab.openxt-syncvm ${D}${sysconfdir}/fstab
}
