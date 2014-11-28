DESCRIPTION = "Linux kernel XenClient ndvm"
COMPATIBLE_MACHINE = "(openxt-ndvm)"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-3.11:"

require linux-xenclient-${PV}.inc
