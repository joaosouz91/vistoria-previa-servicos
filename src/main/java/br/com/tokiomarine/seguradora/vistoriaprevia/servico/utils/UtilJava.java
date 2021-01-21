package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

import br.com.tokiomarine.seguradora.core.util.NumericUtil;

import java.util.Collection;

public class UtilJava {


    private UtilJava() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Valida valor do Ano
     *
     * @param anoStr
     * @return
     */
    public static boolean validarAno(String anoStr) {

        if (NumericUtil.isNumeric(anoStr)) {
            Long anoLong = Long.valueOf(anoStr);
            if (anoLong > 1900 && anoLong < 2100) {
                return true;
            }
        }

        return false;
    }

    public static String addLeftZerosToString(String string, int tillSize) {
        if (string != null) {
            if (string.length() < tillSize) {
                for (int i = string.length(); i < tillSize; i++) {
                    string = "0".concat(string);
                }
            }
        }
        return string;
    }

    public static boolean trueVar(Object o) {

        if (o == null) {
            return false;
        }

        if (o instanceof Object[] && ((Object[]) o).length == 0) {
            return false;

        }
        if (o instanceof Collection && ((Collection<?>) o).isEmpty()) {
            return false;

        }
        if ((o.getClass() == String.class) && o.equals("")) {
            return false;

        }
        if ((o.getClass() == Long.class) && o.equals(0)) {
            return false;

        }
        if ((o.getClass() == Integer.class) && o.equals(0)) {
            return false;

        }
        if ((o.getClass() == Boolean.class) && !((Boolean) o).booleanValue()) {
            return false;

        }

        return (!((o.getClass() == Float.class) && o.equals(new Float(0.0))));

    }


}
