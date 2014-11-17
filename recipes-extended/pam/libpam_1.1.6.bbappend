PR = "openxt-01"

# add SE Linux dependency, so selinux is detected and pam selinux module is build
# unfortunately there is no way to enforce failure when libselinux is not present
DEPENDS += "libselinux"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://etc-config-passwd.patch;patch=1"
EXTRA_OECONF += "--disable-nis"

RDEPENDS_${PN}-runtime += " pam-plugin-selinux"
