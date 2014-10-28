PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "\
  file://enable_hybi_by_default.patch;patch=1 \
  "

EXTRA_OECONF += "\
                --enable-video=no \
                --enable-webgl=no \
               "

# Still need this?
do_install_prepend() {
	cp ${S}/Programs/.libs/jsc ${S}/Programs/jsc-1 || true
}


