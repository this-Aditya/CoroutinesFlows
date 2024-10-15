package flows

sealed class NetworkState(
    val hasWifiOrEthernet: Boolean
) {
    fun hasConnection(needsWifiOrEthernetOnly: Boolean): Boolean = !needsWifiOrEthernetOnly || hasWifiOrEthernet

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NetworkState
        return hasWifiOrEthernet == other.hasWifiOrEthernet
    }
    override fun hashCode(): Int = hasWifiOrEthernet.hashCode()

    object Disconnected : NetworkState(hasWifiOrEthernet = false)
    class Connected(hasWifiOrEthernet: Boolean) : NetworkState(hasWifiOrEthernet)
}


fun main() {
    val state = NetworkState.Disconnected
    println(state.hasConnection(false))
}