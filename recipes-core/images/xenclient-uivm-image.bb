# XenClient UIVM image

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe      \
                    file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

include xenclient-image-common.inc
IMAGE_FEATURES += "package-management"

COMPATIBLE_MACHINE = "(openxt-uivm)"

IMAGE_FSTYPES = "xc.ext3.vhd.gz"

FRIENDLY_NAME = "uivm"

BAD_RECOMMENDATIONS += "avahi-daemon avahi-autoipd"
# The above seems to be broken and we *really* don't want avahi!
PACKAGE_REMOVE = "avahi-daemon avahi-autoipd"

ANGSTROM_EXTRA_INSTALL += " \
                          "
XSERVER ?= "xserver-kdrive-fbdev"
#SPLASH ?= ' ${@base_contains("MACHINE_FEATURES", "screen", "psplash-angstrom", "",d)}'

export IMAGE_BASENAME = "xenclient-uivm-image"

DEPENDS = "packagegroup-base"
IMAGE_INSTALL = "\
    ${ROOTFS_PKGMANAGE} \
    ${XSERVER} \
    modules \
    packagegroup-xenclient-common \
    packagegroup-xenclient-xfce-minimal \
    openssh \
    packagegroup-core-boot \
    packagegroup-base \
    xenfb2 \
    kernel-modules \
    v4v-module \
    libv4v \
    libv4v-bin \
    xinit \
    xprop \
    xrandr \
    midori \
    network-manager-applet \
    network-manager-applet-locale-de \
    network-manager-applet-locale-es \
    network-manager-applet-locale-fr \
    network-manager-applet-locale-ja \
    network-manager-applet-locale-zh-cn \
    gnome-keyring-locale-de \
    gnome-keyring-locale-es \
    gnome-keyring-locale-fr \
    gnome-keyring-locale-ja \
    gnome-keyring-locale-zh-cn \
    iso-codes-locale-de \
    iso-codes-locale-es \
    iso-codes-locale-fr \
    iso-codes-locale-ja \
    iso-codes-locale-zh-cn \
    xterm \
    gconf \
    xblanker \
    xenclient-uivm-xsessionconfig \
    setxkbmap \
    resized \
    xenclient-keymap-sync \
    bootage \
    libx11-locale \
    rsyslog \
    glibc-gconv-libjis \
    glibc-gconv-euc-jp \
    glibc-localedata-translit-cjk-variants \
    glibc-localedata-ja-jp \
    locale-base-ja-jp \
    locale-base-de-de \
    locale-base-es-es \
    locale-base-fr-fr \
    locale-base-zh-cn \
    mobile-broadband-provider-info \
    shutdown-screen \
    fusechat \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    uim \
    anthy \
    uim-gtk2.0 \
    matchbox-keyboard \
    matchbox-keyboard-im \
    ${ANGSTROM_EXTRA_INSTALL}"

# these cause a python dictionary changed size during iteration error
#    gtk+-locale-de \
#    gtk+-locale-es \
#    gtk+-locale-fr \
#    gtk+-locale-ja \
#    gtk+-locale-zh-cn \
#

# OE upgrade - temporarly disabled:

# angstrom-x11-base-depends \
# task-xfce46-base \
# angstrom-gnome-icon-theme-enable \
# battery-applet-4-xfce4 \
# battery-applet-4-xfce4-locale-de \
# battery-applet-4-xfce4-locale-es \
# battery-applet-4-xfce4-locale-fr \
# battery-applet-4-xfce4-locale-ja \
# battery-applet-4-xfce4-locale-zh-cn \
# xsetroot \
# gconf-dbus \
# xkbd \
#


#    angstrom-gpe-task-base \
#    ${SPLASH} \
#
#IMAGE_INSTALL = "\
#    ${XSERVER} \
#    task-base-extended \
#    coreutils \
#    bash \
#    angstrom-x11-base-depends \
#    angstrom-gpe-task-base \
#    angstrom-gpe-task-settings \
#    kernel-modules \
#    hal \
#    devilspie \
#    midori \
#    ${SPLASH} \
#    ${ANGSTROM_EXTRA_INSTALL}"

# IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

strip_unwanted_packages() {
	opkg-cl -f ${IPKGCONF_TARGET} -o ${IMAGE_ROOTFS} ${OPKG_ARGS} -force-depends remove ${PACKAGE_REMOVE}
}

post_rootfs_commands() {
	echo 'x:5:respawn:/bin/su - root -c /usr/bin/startxfce4' >> ${IMAGE_ROOTFS}/etc/inittab;

	# enable ctrlaltdel reboot because PV driver uses ctrl+alt+del to interpret reboot issued via xenstore
	echo 'ca:12345:ctrlaltdel:/sbin/shutdown -t1 -a -r now' >> ${IMAGE_ROOTFS}/etc/inittab;
	sed -i 's|root:x:0:0:root:/home/root:/bin/sh|root:x:0:0:root:/root:/bin/bash|' ${IMAGE_ROOTFS}/etc/passwd;
	echo '1.0.0.0 dom0' >> ${IMAGE_ROOTFS}/etc/hosts;

	# readonly rootfs prevents sshd from creating dirs
	mkdir ${IMAGE_ROOTFS}/root/.ssh;

	# Remove unneeded initscripts provided to dom0 machine types
	if [ -f ${IMAGE_ROOTFS}${sysconfdir}/init.d/urandom ]; then
		rm -f ${IMAGE_ROOTFS}${sysconfdir}/init.d/urandom
		update-rc.d -r ${IMAGE_ROOTFS} urandom remove
	fi
}

# zap root password for release images
ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

ROOTFS_POSTPROCESS_COMMAND += "post_rootfs_commands; strip_unwanted_packages;"

addtask ship before do_build after do_rootfs

inherit image openxt
#inherit validate-package-versions
inherit xenclient-image-src-info
inherit xenclient-image-src-package
inherit xenclient-licences
require xenclient-version.inc


