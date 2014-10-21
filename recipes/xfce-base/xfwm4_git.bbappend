PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://disable-title-bar-right-click-menu.patch \
    "
