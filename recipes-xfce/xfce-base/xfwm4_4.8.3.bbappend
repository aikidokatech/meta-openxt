PR = "openxt-01"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://disable-title-bar-right-click-menu.patch \
            file://resize-full-screen-windows-on-screen-size-change.patch"
