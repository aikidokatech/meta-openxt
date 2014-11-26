FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += " \
    file://no-assume-AT_PAGESIZE-available.patch \
    "
