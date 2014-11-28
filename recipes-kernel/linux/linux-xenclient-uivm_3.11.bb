DESCRIPTION = "Linux kernel XenClient uivm"
COMPATIBLE_MACHINE = "(openxt-uivm)"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-3.11:"

require linux-xenclient-${PV}.inc
