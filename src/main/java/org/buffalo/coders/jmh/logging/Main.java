package org.buffalo.coders.jmh.logging;

/*-
 * #%L
 * jmh-slf4j
 * %%
 * Copyright (C) 2018 - 2019 Buffalo Coders
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.MINUTES)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.MINUTES)
@OperationsPerInvocation(Main.MESSAGES)
public class Main {

	public static final int MESSAGES = 100;

	public static final String MESSAGE = String.join(" ", //
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
			"Nulla metus risus, mattis eget velit sit amet, congue vulputate ligula.",
			"Morbi justo massa, dapibus sit amet nibh nec, finibus cursus lectus.");

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(final String[] args) throws RunnerException {
		new Runner(new OptionsBuilder().include(Main.class.getSimpleName()).build()).run();
	}

	@Benchmark
	public void slf4j() {
		final PrintStream out = System.out;
		final PrintStream err = System.err;

		for (int i = 0; i < MESSAGES; i++) {
			LOG.info(MESSAGE);
		}

		System.setOut(out);
		System.setErr(err);
	}

	@Benchmark
	public void systemerr() {
		for (int i = 0; i < MESSAGES; i++) {
			System.err.println(MESSAGE);
		}
	}

	@Benchmark
	public void systemout() {
		for (int i = 0; i < MESSAGES; i++) {
			System.out.println(MESSAGE);
		}
	}

}
