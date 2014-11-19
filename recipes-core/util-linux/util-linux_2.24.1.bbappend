PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}"

SRC_URI += " file://libmount-fix-selinux-compile.patch \
	"

DEPENDS += "libselinux"

# make ionice a separate package
PACKAGES =+ "util-linux-ionice"
FILES_util-linux-ionice = "${bindir}/ionice"
PROVIDES += "util-linux-ionice"
RRECOMMENDS_${PN} += "util-linux-ionice"

