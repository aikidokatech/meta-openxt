PR = "openxt-01
EXTRA_OECONF += "--with-selinux"
DEPENDS += "libselinux"

# make ionice a separate package
PACKAGES =+ "util-linux-ionice"
FILES_util-linux-ionice = "${bindir}/ionice"
PROVIDES += "util-linux-ionice"
RRECOMMENDS_${PN} += "util-linux-ionice"

