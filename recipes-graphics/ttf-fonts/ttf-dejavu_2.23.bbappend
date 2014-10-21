PR = "openxt-01"

pkg_postinst() {
    if [ -n "$D" ]; then
        exit 0 
    fi
}
