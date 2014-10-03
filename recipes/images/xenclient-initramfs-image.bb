# initramfs image allowing to boot from location as specified on kernel
# command line, from teh choices of block device, loop back images (including
# recursive) and NFS.

COMPATIBLE_MACHINE = "(openxt-dom0)"

IMAGE_FSTYPES = "cpio.gz"
IMAGE_INSTALL = " \
    busybox-static \
    lvm2-static \
    initramfs-xenclient \
    xenclient-initramfs-tpm-config-files \
    kernel-module-tpm \
    kernel-module-tpm-bios \
    kernel-module-tpm-tis \
    tpm-tools-sa \
    xenclient-initramfs-shared-libs \
    xenclient-sha1sum \
    kernel-module-squashfs \
    kernel-module-fbcon \
    kernel-module-tileblit \
    kernel-module-font \
    kernel-module-bitblit \
    kernel-module-softcursor \
    kernel-module-usbhid \
    kernel-module-ehci-hcd \
    kernel-module-ehci-pci \
    kernel-module-uhci-hcd \
    kernel-module-ohci-hcd \
    kernel-module-hid \
    kernel-module-hid-generic \
    module-init-tools-depmod \
    module-init-tools \
    policycoreutils-setfiles \
    "

IMAGE_LINGUAS = ""
IMAGE_DEV_MANAGER = ""
IMAGE_BOOT = "${IMAGE_DEV_MANAGER}"
# Install only ${IMAGE_INSTALL}, not even deps
## Option below did not even seem to exist before updating bitbake and OE
PACKAGE_INSTALL_NO_DEPS = "1"

# Remove any kernel-image that the kernel-module-* packages may have pulled in.
PACKAGE_REMOVE = "kernel-image-* update-modules udev sysvinit opkg* mdev*"

ROOTFS_POSTPROCESS_COMMAND += "opkg-cl ${IPKG_ARGS} -force-depends \
                               remove ${PACKAGE_REMOVE}; "

# Pull in required shared libraries. Having them in a package shared with dom0 causes
# other packages to depend on it no matter what we put in its recipe...
EXTRA_INITRAMFS_LIBS = "\
    lib/ld-linux.so.2 \
    lib/libc.so.6 \
    lib/libdl.so.2 \
    lib/libnss_files.so.2 \
    lib/libcrypto.so.1.0.0 \
    usr/lib/libssl.so.1.0.0 \
    usr/lib/libtspi_sa.so.1"

ROOTFS_POSTPROCESS_COMMAND += " \
    install -d ${IMAGE_ROOTFS}/lib; \
    for a in ${EXTRA_INITRAMFS_LIBS}; do \
	install -m 0755 ${STAGING_DIR_HOST}/$a ${IMAGE_ROOTFS}/lib; \
	${STRIP} ${IMAGE_ROOTFS}/lib/`basename $a`; \
    done; "

IMAGE_PREPROCESS_COMMAND += " \
    rm -rvf ${IMAGE_ROOTFS}/usr/lib/opkg; \
    rm -vf ${IMAGE_ROOTFS}/usr/bin/tpm_sealdata_sa; \
    rm -vf ${IMAGE_ROOTFS}/usr/bin/tpm_unsealdata_sa; \
    rm -vf ${IMAGE_ROOTFS}/etc/init.d/hwclock.sh; \
    rm -vf ${IMAGE_ROOTFS}/etc/init.d/mdev; \
    rm -vf ${IMAGE_ROOTFS}/etc/rcS.d/S06mdev; \
    rm -vf ${IMAGE_ROOTFS}/etc/rcS.d/S98configure; \
    rm -vf ${IMAGE_ROOTFS}/usr/bin/opkg-cl; \
    rm -vf ${IMAGE_ROOTFS}/usr/lib/ipkg; \
    rm -vrf ${IMAGE_ROOTFS}/var/lib; \
    rm -vrf ${IMAGE_ROOTFS}/usr/share/opkg; \
    rm -vrf ${IMAGE_ROOTFS}/etc/ipkg; \
    rm -vrf ${IMAGE_ROOTFS}/etc/opkg; \
"

inherit image
#inherit validate-package-versions
inherit xenclient-image-src-info
inherit xenclient-image-src-package

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe      \
                    file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
