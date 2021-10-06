package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.SpecialOfferDto;
import com.motorola.springShopJPA.model.entity.Article;
import com.motorola.springShopJPA.model.entity.PurchaseOrder;
import com.motorola.springShopJPA.model.entity.SpecialOffer;
import com.motorola.springShopJPA.model.enums.SpecialOfferDiscountType;
import com.motorola.springShopJPA.repository.SpecialOfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SpecialOfferService {

    private final ModelMapper modelMapper;
    private final SpecialOfferRepository specialOfferRepository;


    @Autowired
    public SpecialOfferService(ModelMapper modelMapper, SpecialOfferRepository specialOfferRepository) {
        this.modelMapper = modelMapper;
        this.specialOfferRepository = specialOfferRepository;
    }

    public List<SpecialOfferDto> getAllSpecialOffers(){
        List<SpecialOffer> specialOffers= (List<SpecialOffer>) specialOfferRepository.findAll();
        return specialOffers.stream()
                .map(offer -> modelMapper.map(offer, SpecialOfferDto.class))
                .collect(Collectors.toList());
    }
    public void createNewSpecialOffer(SpecialOfferDto specialOfferDto, SpecialOfferDiscountType discountType){
        final SpecialOffer specialOffer = modelMapper.map(specialOfferDto, SpecialOffer.class);
        specialOffer.setDiscountType(discountType);
        if (discountType==SpecialOfferDiscountType.FREE_PRODUCT){
            specialOffer.setDiscountPercent(BigDecimal.ZERO);
        } else {
            specialOffer.setNumberOfFreeProducts((short) 0);
        }
        specialOfferRepository.save(specialOffer);
    }

    public void deleteSpecialOffer(Long id){
        final Optional<SpecialOffer> specialOffer = specialOfferRepository.findById(id);
        if (specialOffer.isPresent() && specialOffer.get().getPurchaseOrderSet()!=null && specialOffer.get().getPurchaseOrderSet().size()>0){
            final Set<PurchaseOrder> purchaseOrderSet = specialOffer.get().getPurchaseOrderSet();
            for (PurchaseOrder order:purchaseOrderSet) {
                order.setSpecialOffer(null);
                final Optional<BigDecimal> totalPrice = order.getArticles().values().stream()
                        .map(Article::getArticleTotalPrice)
                        .reduce(BigDecimal::add);
                totalPrice.ifPresent(order::setTotalValue);
            }
        }
        specialOfferRepository.deleteById(id);
    }

    public SpecialOfferDiscountType[] getAllDiscountTypes() {
        return SpecialOfferDiscountType.values();
    }
}
