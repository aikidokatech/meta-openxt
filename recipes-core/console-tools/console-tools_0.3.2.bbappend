PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += " \
           file://config \
           file://unicode-keysym-with-plus.patch \
"

#export SUBDIRS = "fontfiletools vttools kbdtools screenfonttools contrib examples po intl compat"

CFLAGS_append += " -Wno-attributes "

#EXTRA_OEMAKE = " -C lib "

#do_compile () {
#	oe_runmake -C lib
#	oe_runmake 'SUBDIRS=${SUBDIRS}'
#}

# acpaths = "-I ${WORKDIR}/config"

do_install () {
	autotools_do_install
	mv ${D}${bindir}/chvt ${D}${bindir}/chvt.${PN}
	mv ${D}${bindir}/deallocvt ${D}${bindir}/deallocvt.${PN}
	mv ${D}${bindir}/openvt ${D}${bindir}/openvt.${PN}
	mv ${D}${bindir}/showkey ${D}${bindir}/showkey.${PN}
}

pkg_postinst_${PN} () {
	update-alternatives --install ${bindir}/chvt chvt chvt.${PN} 100
	update-alternatives --install ${bindir}/deallocvt deallocvt deallocvt.${PN} 100
	update-alternatives --install ${bindir}/openvt openvt openvt.${PN} 100
	update-alternatives --install ${bindir}/showkey showkey showkey.${PN} 100
}

pkg_prerm_${PN} () {
	update-alternatives --remove chvt chvt.${PN}
	update-alternatives --remove deallocvt deallocvt.${PN}
	update-alternatives --remove openvt openvt.${PN}
	update-alternatives --remove showkey showkey.${PN}
}

