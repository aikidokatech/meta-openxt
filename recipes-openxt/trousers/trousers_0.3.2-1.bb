SRC_URI[md5sum] = "b824764fa87e36be27c0bc29f84dda55"
SRC_URI[sha256sum] = "27bb906cc06d29ead85ca95e7268401b914c2eac81663a7db0e241f1178a4ba4"
DEPENDS = "openssl"
LICENSE = "CPL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3728dd9198d68b49f7f9ed1f3f50ba14"
PR = "openxt-01"

#RDEPENDS_${PN} += "tinylogin"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI = "http://downloads.sourceforge.net/${PN}/${PN}-${PV}.tar.gz \
           file://trousers-no-groups-users.patch;patch=1 \
           file://trousers-tcsd-conf.patch;patch=1 \
           file://trousers-standalone.patch;patch=1 \
           file://trousers_compile_with_newer_gcc.patch;patch=1 \
           file://trousers.initscript \
           file://45-trousers.rules \
"

inherit xenclient update-rc.d useradd

INITSCRIPT_PACKAGES = "${PN}"

INITSCRIPT_NAME_${PN} = "trousers"
INITSCRIPT_PARAMS_${PN} = "defaults 85"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--gid 421 tss"
USERADD_PARAM_${PN} = "--system --home-dir /boot/system/tpm --shell /bin/false --gid tss --uid 421 tss"

pkg_postinst_${PN}() {
        chown tss:tss $D/etc/tcsd.conf
        mkdir -p $D/boot/system/tpm
        chown tss:tss $D/boot/system/tpm
        install -o tss -g tss -m 600 $D/usr/share/trousers/system.data.auth $D/boot/system/tpm/system.data
}

inherit autotools

do_custom_install() {
	install -m 0755 -d ${D}${datadir}/trousers
	install -m 0644 ${S}/dist/system.data.auth ${D}${datadir}/trousers/
	install -m 0644 ${S}/dist/system.data.noauth ${D}${datadir}/trousers/
        install -m 0755 -d ${D}/etc/init.d
        install -m 0755 ${WORKDIR}/trousers.initscript ${D}/etc/init.d/trousers
        install -m 0755 -d ${D}/etc/udev/rules.d
        install -m 0644 ${WORKDIR}/45-trousers.rules ${D}/etc/udev/rules.d
}

addtask custom_install after do_install before do_package

#do_install_append_openxt-dom0() {
#	# some files are already provided by xenclient-initramfs-tpm-config-files
#	rm -f ${D}${sysconfdir}/tcsd.conf
#}

PACKAGES =+ "${PN}-data"
FILES_${PN}-data = "${datadir}/trousers/system.data.auth \
	${datadir}/trousers/system.data.noauth \
"

RPROVIDES_${PN} =+ "${PN}-data"
