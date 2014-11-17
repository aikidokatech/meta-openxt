# XenClient secure backend-domain image
LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe      \
                    file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

include xenclient-image-common.inc
IMAGE_FEATURES += "package-management"

COMPATIBLE_MACHINE = "(openxt-ndvm)"

IMAGE_FSTYPES = "xc.ext3.vhd.gz"

#IMAGE_LINGUAS = ""

FRIENDLY_NAME = "ndvm"

BAD_RECOMMENDATIONS += "avahi-daemon avahi-autoipd"
# The above seems to be broken and we *really* don't want avahi!
PACKAGE_REMOVE = "avahi-daemon avahi-autoipd hicolor-icon-theme"

export IMAGE_BASENAME = "xenclient-ndvm-image"

ANGSTROM_EXTRA_INSTALL += ""

DEPENDS = "packagegroup-base"
IMAGE_INSTALL = "\
    ${ROOTFS_PKGMANAGE} \
    modules \
    packagegroup-core-boot \
    packagegroup-base \
    packagegroup-xenclient-common \
    util-linux-mount \
    util-linux-umount \
    busybox \
    openssh \
    kernel-modules \
    libv4v \
    libv4v-bin \
    dbus \
    xenclient-dbusbouncer \
    networkmanager \
    xenclient-toolstack \
    intel-e1000e \
    intel-e1000e-conf \
    linux-firmware \
    rt2870-firmware \
    rt3572 \
    bridge-utils \
    iptables \
    xenclient-ndvm-tweaks \
    ipsec-tools \
    rsyslog \
    ${ANGSTROM_EXTRA_INSTALL} \
    xenclient-udev-force-discreet-net-to-eth0 \
    v4v-module \
    xen-tools-libxenstore \
    xen-tools-xenstore-utils \
    wget \
    ethtool \
    carrier-detect \
    xenclient-nws \
    modemmanager \
    ppp \
    iputils-ping \
"

# Packages disabled for Linux3 to be fixed
# rt5370

inherit selinux-image openxt
#inherit validate-package-versions
inherit xenclient-image-src-info
inherit xenclient-image-src-package
inherit xenclient-licences
require xenclient-version.inc

#IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

#zap root password for release images
ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

tweak_passwd() {
	sed -i 's|root:x:0:0:root:/home/root:/bin/sh|root:x:0:0:root:/root:/bin/bash|' ${IMAGE_ROOTFS}/etc/passwd;
}

tweak_hosts() {
	echo '1.0.0.0 dom0' >> ${IMAGE_ROOTFS}/etc/hosts;
}

# enable ctrlaltdel reboot because PV driver uses ctrl+alt+del to interpret reboot issued via xenstore
enable_three_fingered_salute() {
	echo 'ca:12345:ctrlaltdel:/sbin/shutdown -t1 -a -r now' >> ${IMAGE_ROOTFS}/etc/inittab;
}

# Move resolv.conf to /var/volatile/etc, as rootfs is readonly
relocate_resolv() {
	rm -f ${IMAGE_ROOTFS}/etc/resolv.conf;
	ln -s /var/volatile/etc/resolv.conf ${IMAGE_ROOTFS}/etc/resolv.conf;
}

remove_unwanted_packages() {
	opkg-cl -f ${IPKGCONF_TARGET} -o ${IMAGE_ROOTFS} ${OPKG_ARGS} -force-depends remove ${PACKAGE_REMOVE};
}

remove_initscripts() {
    # Remove unneeded initscripts
    if [ -f ${IMAGE_ROOTFS}${sysconfdir}/init.d/urandom ]; then
        rm -f ${IMAGE_ROOTFS}${sysconfdir}/init.d/urandom
        update-rc.d -r ${IMAGE_ROOTFS} urandom remove
    fi
}

ROOTFS_POSTPROCESS_COMMAND += "tweak_passwd; tweak_hosts; enable_three_fingered_salute; relocate_resolv; remove_unwanted_packages; remove_initscripts; "

addtask do_ship after do_rootfs before do_licences
