DESCRIPTION = "Linux kernel XenClient syncvm"
COMPATIBLE_MACHINE = "(openxt-syncvm)"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-3.11:"

require linux-xenclient-${PV}.inc
