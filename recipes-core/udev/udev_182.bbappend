PR = "openxt-01"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF += "--enable-static --with-selinux"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

inherit update-rc.d

INITSCRIPT_PARAMS_udev = "start 03 S ."

DEPENDS += " libselinux "

SRC_URI += " \
        file://${PACKAGE_ARCH}-init \
        file://usb-hid-no-autosleep.patch;patch=1 \
        file://disable-cdrom-locking-by-dom0.patch;patch=2 \
        "

do_install_append () {
	mkdir ${D}/${base_sbindir}
	(cd ${D}${base_sbindir}; ln -s ..${base_libdir}/udev/udevd .)

	# Remove the default udev init script to install our own
	rm -f ${D}${sysconfdir}/init.d/udev
	install -m 0755 ${WORKDIR}/${PACKAGE_ARCH}-init ${D}${sysconfdir}/init.d/udev
}
