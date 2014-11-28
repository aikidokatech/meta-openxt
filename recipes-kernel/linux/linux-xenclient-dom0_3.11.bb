DESCRIPTION = "Linux kernel XenClient dom0"
COMPATIBLE_MACHINE = "(openxt-dom0)"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-3.11:"

require linux-xenclient-${PV}.inc

