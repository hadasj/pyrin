package cz.i.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cz.i.entity.model.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueResultHandler implements ResultHandler<FactValue> {

    @Override
    public void handleResult(ResultContext<? extends FactValue> resultContext) {
        FactValue factValue = resultContext.getResultObject();
        Object value = null;
        if (!StringUtils.isEmpty(factValue.getValueValue()))
            switch (factValue.getType()) {
                case STRING:
                    value = factValue.getValueValue();
                    break;
                case INT:
                    value = Integer.valueOf(factValue.getValueValue());
                    break;
                case LONG:
                    value = Long.valueOf(factValue.getValueValue());
                    break;
                case DOUBLE:
                    value = Double.valueOf(factValue.getValueValue());
                    break;
                case BIG_DECIMAL:
                    value = new BigDecimal(factValue.getValueValue());
                    break;
                case TIMESTAMP:
                    value = LocalDateTime.parse(factValue.getValueValue());
                    break;
                case DIMENSION_VALUE:
                    if (factValue.getDimension() != null)
                        value = factValue.getDimension().getAlias();
                    break;
            }
        factValue.setValue(value);
    }
}
