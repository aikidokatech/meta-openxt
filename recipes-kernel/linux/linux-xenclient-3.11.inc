inherit xenclient
inherit xenclient-pq

require linux.inc

DEPENDS += "bc-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KERNEL_IMAGETYPE = "bzImage"

SRCREV_FORMAT = "kernel_patchqueue"

PV = "3.11+git${SRCPV}"

SRCREV_patchqueue = "${AUTOREV}"

SRC_URI = "http://mirror.openxt.org/linux-3.11.10.4.tar.gz;name=kernel \
           git://${OPENXT_GIT_MIRROR}/linux-3.11-pq.git;protocol=${OPENXT_GIT_PROTOCOL};branch=${OPENXT_BRANCH};destsuffix=patchqueue;name=patchqueue \
           file://defconfig"

S = "${WORKDIR}/linux-3.11.10.4"
 
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"


