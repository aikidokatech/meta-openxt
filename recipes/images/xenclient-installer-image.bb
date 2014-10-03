# Part 1 of the XenClient host installer.
#
# This is responsible for retrieving the XenClient repository and extracting
# and running part 2 of the host installer, which contains the logic to install
# or upgrade a specific version of XenClient.

include xenclient-image-common.inc

COMPATIBLE_MACHINE = "(openxt-dom0)"
IMAGE_INITSCRIPTS = "initscripts"

PR = "r15"

SRC_URI += " \
            file://network.ans \
            file://network_upgrade.ans \
            file://network_manual.ans \
            file://network_download_win.ans \
            file://network_manual_download_win.ans \
            file://pxelinux.cfg \
            file://isolinux.cfg \
            file://bootmsg.txt \
"

ANGSTROM_EXTRA_INSTALL += ""

export IMAGE_BASENAME = "xenclient-installer-image"

DEPENDS = "packagegroup-base packagegroup-xenclient-installer"

IMAGE_INSTALL = "\
    ${ROOTFS_PKGMANAGE} \
    ${IMAGE_INITSCRIPTS} \
    modules-installer \
    linux-firmware \
    packagegroup-core-boot \
    packagegroup-base \
    packagegroup-xenclient-common \
    packagegroup-xenclient-installer \
    ${ANGSTROM_EXTRA_INSTALL}"

IMAGE_FSTYPES = "cpio.gz"

# IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

# Create /init symlink
ROOTFS_POSTPROCESS_COMMAND += " \
    ln -s sbin/init ${IMAGE_ROOTFS}/init;"

# Update /etc/inittab
ROOTFS_POSTPROCESS_COMMAND += " \
    sed -i '/^1:/d' ${IMAGE_ROOTFS}/etc/inittab; \
    { \
        echo '1:2345:once:/install/part1/autostart-main < /dev/tty1 > /dev/tty1'; \
        echo '2:2345:respawn:/usr/bin/tail -F /var/log/installer > /dev/tty2'; \
        echo '3:2345:respawn:/sbin/getty 38400 tty3'; \
        echo '4:2345:respawn:/usr/bin/tail -F /var/log/messages > /dev/tty4'; \
        echo '5:2345:respawn:/sbin/getty 38400 tty5'; \
        echo '6:2345:respawn:/sbin/getty 38400 tty6'; \
        echo '7:2345:respawn:/install/part1/autostart-status < /dev/tty7 > /dev/tty7'; \
        echo 'ca::ctrlaltdel:/sbin/reboot'; \
    } >> ${IMAGE_ROOTFS}/etc/inittab;"

# Update /etc/fstab
ROOTFS_POSTPROCESS_COMMAND += " \
    sed -i '/^\/dev\/mapper\/xenclient/d' ${IMAGE_ROOTFS}/etc/fstab;"

# Update /etc/network/interfaces
ROOTFS_POSTPROCESS_COMMAND += " \
    { \
        echo 'auto lo'; \
        echo 'iface lo inet loopback'; \
    } > ${IMAGE_ROOTFS}/etc/network/interfaces;"

# Password files are expected in /config
ROOTFS_POSTPROCESS_COMMAND += " \
    mkdir -p ${IMAGE_ROOTFS}/config/etc; \
    mv ${IMAGE_ROOTFS}/etc/shadow ${IMAGE_ROOTFS}/config/etc/shadow; \
    mv ${IMAGE_ROOTFS}/etc/passwd ${IMAGE_ROOTFS}/config/etc/passwd; \
    ln -s /config/etc/shadow ${IMAGE_ROOTFS}/etc/shadow; \
    ln -s /config/etc/passwd ${IMAGE_ROOTFS}/etc/passwd;"

# Use bash as login shell
ROOTFS_POSTPROCESS_COMMAND += " \
    sed -i 's|root:x:0:0:root:/home/root:/bin/sh|root:x:0:0:root:/root:/bin/bash|' \
        ${IMAGE_ROOTFS}/config/etc/passwd;"

# Don't start blktapctrl daemon
ROOTFS_POSTPROCESS_COMMAND += " \
    rm -f ${IMAGE_ROOTFS}/etc/init.d/blktap; \
    rm -f ${IMAGE_ROOTFS}/etc/rc*.d/*blktap;"

# Create file to identify this as the host installer filesystem
ROOTFS_POSTPROCESS_COMMAND += " \
    touch ${IMAGE_ROOTFS}/etc/xenclient-host-installer;"

do_rootfs_append() {
    # What line no is this?
}

inherit image
inherit xenclient-image-src-info
inherit xenclient-image-src-package
inherit xenclient-licences
require xenclient-version.inc

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe      \
                    file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

python() {
    bb.data.delVarFlag("do_fetch", "noexec", d);
    bb.data.delVarFlag("do_unpack", "noexec", d);
    bb.data.delVarFlag("do_patch", "noexec", d);
    bb.data.delVarFlag("do_configure", "noexec", d);
    bb.data.delVarFlag("do_compile", "noexec", d);
    bb.data.delVarFlag("do_install", "noexec", d);
}
do_rootfs[depends] += "xenclient-installer-image:do_install"
