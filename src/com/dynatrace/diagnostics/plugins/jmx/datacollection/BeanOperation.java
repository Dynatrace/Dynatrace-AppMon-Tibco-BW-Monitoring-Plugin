package com.dynatrace.diagnostics.plugins.jmx.datacollection;

import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;


public class BeanOperation {

	private static final Logger log = Logger.getLogger(BeanOperation.class.getName());
    protected static final Pattern CMD_LINE_ARGS_PATTERN = Pattern.compile("^([^=]+)(?:(?:\\=)(.+))?$");
    final BeanOperation this$0 = BeanOperation.this;

    protected class CommandParse {

        private String cmd;
        private String args[];

        private void parse(String command)
                throws ParseException {
            Matcher m = BeanOperation.CMD_LINE_ARGS_PATTERN.matcher(command);
            if (m == null || !m.matches()) {
                throw new ParseException((new StringBuilder()).append("Failed parse of ").append(command).toString(), 0);
            }
            cmd = m.group(1);
            if (m.group(2) != null && m.group(2).length() > 0) {
                args = m.group(2).split(",");
            } else {
                args = null;
            }
        }

        protected String getCmd() {
            return cmd;
        }

        protected String[] getArgs() {
            return args;
        }

        protected CommandParse(String command)
                throws ParseException {
            parse(command);
        }
    }

    protected Object doBeanOperation(MBeanServerConnection mbsc, ObjectInstance instance, String command, MBeanOperationInfo infos[])
            throws Exception {
        CommandParse parse = new CommandParse(command);
        SubCommand newFeatures = new SubCommand();
        MBeanOperationInfo op = (MBeanOperationInfo) newFeatures.getFeatureInfo(infos, parse.getCmd());
        Object result;
        if (op == null) {
            result = (new StringBuilder()).append("Operation ").append(parse.getCmd()).append(" not found.").toString();
        } else {
            MBeanParameterInfo paraminfos[] = op.getSignature();
            int paraminfosLength = paraminfos != null ? paraminfos.length : 0;
            int objsLength = parse.getArgs() != null ? parse.getArgs().length : 0;
            if (paraminfosLength != objsLength) {
                result = "Passed param count does not match signature count";  // Has a subcommand that goes with it....
            } else {
                String signature[] = new String[paraminfosLength];
                Object params[] = paraminfosLength != 0 ? new Object[paraminfosLength] : null;
                for (int i = 0; i < paraminfosLength; i++) {
                    MBeanParameterInfo paraminfo = paraminfos[i];
                    Constructor<?> c = Class.forName(paraminfo.getType()).getConstructor(new Class[]{ //java/lang/String
                    });
                    params[i] = c.newInstance(new Object[]{
                        parse.getArgs()[i]
                    });
                    signature[i] = paraminfo.getType();
                }

                result = mbsc.invoke(instance.getObjectName(), parse.getCmd(), params, signature);
                if (log.isLoggable(Level.FINE)){
                	log.fine(parse.getCmd() + " - This is the command that was passed.");
                }
            }
        }
        return result;
    }
}
