PR = "openxt-01"

pkg_postinst_${PN}() {
    if [ -z "$D" ]; then
        update-fonts 
    fi
}
