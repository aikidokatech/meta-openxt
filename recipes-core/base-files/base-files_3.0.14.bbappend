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

dirs1777 += " ${localstatedir}/lock"
dirs755 += " \
	${localstatedir}/volatile/run \
	${localstatedir}/volatile/lock/subsys \
	${localstatedir}/cache \
	${localstatedir}/log \
	/mnt /media /media/card /media/cf /media/net /media/ram \
	/media/union /media/realroot /media/hdd \
	/media/mmc1"

media = "card cf net ram"
volatiles = ""

# Gotta override because we don't want the symlinks provided for run and lock
do_install () {
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done
	for d in ${dirs1777}; do
		install -m 1777 -d ${D}$d
	done
	for d in ${dirs2775}; do
		install -m 2755 -d ${D}$d
	done
	for d in ${volatiles}; do
		ln -sf volatile/$d ${D}${localstatedir}/$d
	done

	${BASEFILESISSUEINSTALL}

	rotation=`cat ${WORKDIR}/rotation`
	if [ "$rotation" != "0" ]; then
 		install -m 0644 ${WORKDIR}/rotation ${D}${sysconfdir}/rotation
	fi

	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
	install -m 0644 ${WORKDIR}/filesystems ${D}${sysconfdir}/filesystems
	install -m 0644 ${WORKDIR}/usbd ${D}${sysconfdir}/default/usbd
	install -m 0644 ${WORKDIR}/profile ${D}${sysconfdir}/profile
	sed -i 's#ROOTHOME#${ROOT_HOME}#' ${D}${sysconfdir}/profile
	install -m 0644 ${WORKDIR}/shells ${D}${sysconfdir}/shells
	install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
	install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
	install -m 0644 ${WORKDIR}/inputrc ${D}${sysconfdir}/inputrc
	install -m 0644 ${WORKDIR}/nsswitch.conf ${D}${sysconfdir}/nsswitch.conf
	install -m 0644 ${WORKDIR}/host.conf ${D}${sysconfdir}/host.conf
	install -m 0644 ${WORKDIR}/motd ${D}${sysconfdir}/motd

	ln -sf /proc/mounts ${D}${sysconfdir}/mtab
}

do_install_append() {
	for d in ${media}; do
		ln -sf /media/$d ${D}/mnt/$d
	done

}

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
