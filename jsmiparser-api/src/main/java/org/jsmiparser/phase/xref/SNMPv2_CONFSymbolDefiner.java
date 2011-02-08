/**
 * Copyright 2011-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsmiparser.phase.xref;

public class SNMPv2_CONFSymbolDefiner extends AbstractSymbolDefiner {

    protected SNMPv2_CONFSymbolDefiner() {
        super("SNMPv2-CONF");
    }

    @Override
    protected void defineSymbols() {
        super.defineSymbols();

        addMacro("OBJECT-GROUP");
        addMacro("NOTIFICATION-GROUP");
        addMacro("MODULE-COMPLIANCE");
        addMacro("AGENT-CAPABILITIES");
    }

}
