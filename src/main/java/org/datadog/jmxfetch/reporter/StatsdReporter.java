package org.datadog.jmxfetch.reporter;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.datadog.jmxfetch.Instance;
import org.datadog.jmxfetch.JMXAttribute;

public class StatsdReporter extends Reporter {

    private StatsDClient statsDClient;
    private int statsdPort;
    private long initializationTime;

    public StatsdReporter(int statsdPort) {
        this.statsdPort = statsdPort;
        this.init();
    }

    private void init() {
        initializationTime = System.currentTimeMillis();
        statsDClient = new NonBlockingStatsDClient(null, "localhost", this.statsdPort, new String[]{});
    }

    protected void sendMetricPoint(String metricName, double value, String[] tags) {
        if (System.currentTimeMillis() - this.initializationTime > 300 * 1000) {
            this.statsDClient.stop();
            init();
        }
        statsDClient.gauge(metricName, value, tags);
    }

    public void displayMetricReached() {
        throw new UnsupportedOperationException();
    }

    public void displayMatchingAttributeName(JMXAttribute jmxAttribute, int rank, int limit) {
        throw new UnsupportedOperationException();
    }

    public void displayNonMatchingAttributeName(JMXAttribute jmxAttribute) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void displayInstanceName(Instance instance) {
        throw new UnsupportedOperationException();
    }

    public int getStatsdPort() {
        return statsdPort;
    }
}
