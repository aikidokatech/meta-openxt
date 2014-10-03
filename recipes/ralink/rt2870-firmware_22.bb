SRC_URI[md5sum] = "c5a93b466532a5617da6b203cabab62b"
SRC_URI[sha256sum] = "d24591a8529b0a609cc3c626ecee96484bb29b2c020260b82f6025459c11f263"
LICENSE = "Ralink"
LIC_FILES_CHKSUM = "file://LICENSE.ralink-firmware.txt;md5=682eac07428a4e8f138bc082f090ecac"
DESCRIPTION = "Firmare for RT2870/RT2770/RT3572/RT3070"

SRC_URI = "${OPENXT_MIRROR}/RT2870_Firmware_V22.zip"

S = "${WORKDIR}/RT2870_Firmware_V22"

inherit module-base

do_install() {
    # Create the destination
    install -d ${D}/lib/firmware/${KERNEL_VERSION}/rt2870/

    # Walk all files in the build dir
    for a in ${WORKDIR}/RT2870_Firmware_V22/*; do
        # Only run install on a file, ignore directories
        if [ -f $a ]; then
            install -m 0644 $a ${D}/lib/firmware/${KERNEL_VERSION}/rt2870/
        fi
    done
}

FILES_${PN} = "/lib/firmware/${KERNEL_VERSION}/rt2870/"

MACHINE_KERNEL_PR_append = "a"
