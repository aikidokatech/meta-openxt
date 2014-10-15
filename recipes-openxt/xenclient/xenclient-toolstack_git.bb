inherit findlib
DESCRIPTION = "XenClient toolstack"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=321bf41f280cf805086dd5a720b37785"
DEPENDS += " ocaml-cross ocaml-dbus ocaml-camomile xen-tools"
RDEPENDS_${PN} = " xen-tools-xenstore-utils "
RDEPENDS_openxt-ndvm += " db-tools"

DEPENDS_append_openxt-nilfvm += " ${@deb_bootstrap_deps(d)} "

inherit xenclient
inherit ${@"xenclient-simple-deb"if(bb.data.getVar("MACHINE",d,1)=="openxt-nilfvm")else("null")}

PACKAGES += "${PN}-libs-dbg ${PN}-libs-staticdev ${PN}-libs-dev ${PN}-libs"
FILES_${PN}-libs-dbg = "${ocamllibdir}/*/.debug/*"
FILES_${PN}-libs-dev = "${ocamllibdir}/*/*.so"
FILES_${PN}-libs-staticdev = "${ocamllibdir}/*/*.a"
FILES_${PN}-libs = "${ocamllibdir}/*"

DEB_SUITE = "wheezy"
DEB_ARCH = "i386"

DEB_NAME = "nilfvm-xenclient-toolstack"
DEB_DESC="The nilfvm XenClient toolstack package"
DEB_DESC_EXT="This package provides the  nilfvm XenClient toolstack scrips."
DEB_SECTION="misc"
DEB_PKG_MAINTAINER = "Citrix Systems <customerservice@citrix.com>"



# Ocaml stuff is built with the native compiler with "-m32".
CFLAGS_append = " -I${OCAML_HEADERS}"

SRC_URI = "${OPENXT_GIT_MIRROR}/toolstack.git;protocol=git;tag=${OPENXT_TAG}	\
           file://vif"

PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}/git"


do_compile() {
        make V=1 XEN_DIST_ROOT="${STAGING_DIR}"
}
OCAML_INSTALL_LIBS  = "libs/uuid libs/stdext libs/mmap \
                      libs/base64 libs/scsi libs/udev \
                      libs/json libs/jsonrpc libs/http \
                      libs/log libs/xc libs/eventchn \
                      libs/xb libs/xs libs/netdev \
		      libs/common"

do_configure_openxt-nilfvm() {
        :
}

do_compile_openxt-nilfvm() {
        :
}

do_install() {
        make DESTDIR=${D} V=1 install
        rm -f ${D}/etc/xen/scripts/vif
        install -d ${D}/etc/xen/scripts
        install -m 0755 ${WORKDIR}/vif ${D}/etc/xen/scripts/vif
        # install ocaml libraries required by other packages
        mkdir -p "${D}${ocamllibdir}"
        for ocaml_lib in ${OCAML_INSTALL_LIBS}
        do
                oe_runmake -C $ocaml_lib DESTDIR=${D} V=1 install || exit 1
        done
}

do_install_append_openxt-nilfvm() {
	## to generate deb package
	DEB_DO_NOT_INCLUDE="usr/bin/ usr/lib/"
	do_simple_deb_package
}

#do_stage() {
#	make V=1 STAGING_DIR="${STAGING_DIR}" STAGING_LIBDIR="${STAGING_LIBDIR}" stage
#}

# Avoid GNU_HASH check for the ocaml binaries
INSANE_SKIP_${PN} = "1"
