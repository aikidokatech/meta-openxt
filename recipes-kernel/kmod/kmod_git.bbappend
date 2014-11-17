PR = "openxt-01"

# We only want libkmod2 really...
EXTRA_OECONF := "${@oe_filter_out('--enable-tools', '${EXTRA_OECONF}', d)}"
EXTRA_OECONF := "${@oe_filter_out('--enable-logging', '${EXTRA_OECONF}', d)}"
EXTRA_OECONF += " --disable-tools --disable-logging --without-bashcompletiondir"
