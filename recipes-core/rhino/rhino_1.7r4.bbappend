PR = "openxt-01"

# Fix bad url from original recipe
SRC_URI = " \
	https://github.com/mozilla/rhino/archive/Rhino1_7R4_RELEASE.zip \
	file://rhino \
	file://rhino-jsc \
	"

SRC_URI[md5sum] = "d56e7ce505f96af1e00c5357e417177c"
SRC_URI[sha256sum] = "8a22e019211f53f761061983c9047f4fe423e3266fd41c51d6f27d305ea2189c"

# Dirty hack to use the right source dir
S = "${WORKDIR}/rhino-Rhino1_7R4_RELEASE"

