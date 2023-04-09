package org.andstatus.util

object CheckHeapSize {
    fun printHeapSize() {
        val heapSize = Runtime.getRuntime().totalMemory()

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
        // Any attempt will result in an OutOfMemoryException.
        val heapMaxSize = Runtime.getRuntime().maxMemory()

        // Get amount of free memory within the heap in bytes. This size will increase
        // after garbage collection and decrease as new objects are created.
        val heapFreeSize = Runtime.getRuntime().freeMemory()
        println("heapSize:" + formatSize(heapSize) +
                ", heapMaxSize:" + formatSize(heapMaxSize) +
                ", heapFreeSize:" + formatSize(heapFreeSize))
    }

    private fun formatSize(v: Long): String {
        if (v < 1024) return "$v B"
        val z = (63 - java.lang.Long.numberOfLeadingZeros(v)) / 10
        return String.format("%.1f %sB", v.toDouble() / (1L shl z * 10), " KMGTPE"[z])
    }
}