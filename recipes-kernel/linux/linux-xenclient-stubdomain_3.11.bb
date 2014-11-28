DESCRIPTION = "Linux kernel XenClient stubdomain"
COMPATIBLE_MACHINE = "(openxt-stubdomain)"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-3.11:"

require linux-xenclient-${PV}.inc
