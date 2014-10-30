DESCRIPTION = "Xfce configuration daemon and utilities"
SECTION = "x11/wm"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
DEPENDS = "dbus-glib libxfce4util perl-native"

PR = "r0"

inherit xfce

EXTRA_OECONF += "PERL=${STAGING_DIR_TARGET}/usr/bin/perl"

FILES_${PN} += "${libdir}/xfce4/xfconf/xfconfd \
                ${datadir}/dbus-1/services/org.xfce.Xfconf.service"

SRC_URI[md5sum] = "70b4185aa7eed8a85f015f16c5c86d36"
SRC_URI[sha256sum] = "9860737539fab2b852e8c07c50c205a5fbe894b08bcb4488a70e3fd6c75993cb"

CFLAGS_append = " -Wno-deprecated-declarations " 
