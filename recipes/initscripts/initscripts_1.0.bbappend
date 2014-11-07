PR = "openxt-01"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI_OVERRIDES_PACKAGE_ARCH = "1"

# Some machines need some special scripts.  Individual
# image recipes may trim down after do_rootfs.  May
# switch to full overrides here instead.
SRC_URI_append += "\
           file://finish.sh \
           file://mount-special"

SRC_URI_openxt-uivm = "file://functions \
           file://halt \
           file://umountfs \
           file://mountall.sh \
           file://bootmisc.sh \
           file://reboot \
           file://single \
           file://sendsigs \
           file://mount-special \
           file://populate-volatile.sh \
           file://volatiles \
           file://save-rtc.sh \
"

do_install_append_openxt-dom0 () {
#
# install device independent scripts
#
	install -m 0755    ${WORKDIR}/finish.sh		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/mount-special	${D}${sysconfdir}/init.d
	
#
# Create runlevel links
#
	#ln -sf		../init.d/mount-special	${D}${sysconfdir}/rcS.d/S00mount-special
	#ln -sf		../init.d/finish.sh	${D}${sysconfdir}/rcS.d/S99finish.sh
	update-rc.d -r ${D} mount-special start 00 S .
	update-rc.d -r ${D} finish.sh start 99 S .

}

do_install_openxt-stubdomain() {

}

do_install_openxt-uivm () {
#
# Create directories and install device independent scripts
#
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -d ${D}${sysconfdir}/rc0.d
	install -d ${D}${sysconfdir}/rc1.d
	install -d ${D}${sysconfdir}/rc2.d
	install -d ${D}${sysconfdir}/rc3.d
	install -d ${D}${sysconfdir}/rc4.d
	install -d ${D}${sysconfdir}/rc5.d
	install -d ${D}${sysconfdir}/rc6.d
	install -d ${D}${sysconfdir}/default
	install -d ${D}${sysconfdir}/default/volatiles

	install -m 0755    ${WORKDIR}/functions		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/bootmisc.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/halt		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/mountall.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/reboot		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/sendsigs		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/single		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/mount-special	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/populate-volatile.sh ${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/save-rtc.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/mount-special	${D}${sysconfdir}/init.d
	install -m 0644    ${WORKDIR}/volatiles		${D}${sysconfdir}/default/volatiles/00_core

#
# Install device dependent scripts
#
	install -m 0755 ${WORKDIR}/umountfs	${D}${sysconfdir}/init.d/umountfs
#
# Create runlevel links
#
	update-rc.d -r ${D} sendsigs start 20 0 6 .
	update-rc.d -r ${D} umountfs start 40 0 6 .
	update-rc.d -r ${D} reboot start 90 6 .
	update-rc.d -r ${D} halt start 90 0 .
	update-rc.d -r ${D} save-rtc.sh start 25 0 6 .
	update-rc.d -r ${D} mountall.sh start 35 S .
	update-rc.d -r ${D} bootmisc.sh start 55 S .
	update-rc.d -r ${D} populate-volatile.sh start 37 S .
	update-rc.d -r ${D} mount-special start 00 S .
}

pkg_postinst_${PN}_append() {
    if [ -n "$D" ]; then
        $D/etc/init.d/populate-volatile.sh update
    fi
}
