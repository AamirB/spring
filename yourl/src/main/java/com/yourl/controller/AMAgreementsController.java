//package com.homeunion.view.rest.controllers;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.homeunion.common.util.NullCheckUtil;
//import com.homeunion.domain.dto.AMAPackageInfoDTO;
//import com.homeunion.domain.dto.AMAgreementsRequestDto;
//import com.homeunion.domain.service.AMAgreementsService;
//import com.homeunion.domain.service.AssetsService;
//import com.homeunion.domain.service.CodeListService;
//import com.homeunion.domain.service.DocumentService;
//import com.homeunion.domain.service.LeaseService;
//import com.homeunion.embrace.constants.CommonConstants;
//import com.homeunion.embrace.util.date.DateFormatter;
//import com.homeunion.persistence.entities.embrace.AMAgreements;
//import com.homeunion.persistence.entities.embrace.Asset;
//import com.homeunion.persistence.entities.embrace.Document;
//import com.homeunion.persistence.entities.embrace.Lease;
//import com.homeunion.view.rest.presentation.AMAPackageInfoResponse;
//import com.homeunion.view.rest.presentation.AMAgreementDocumentRequest;
//import com.homeunion.view.rest.presentation.AMAgreementsRequest;
//import com.homeunion.view.rest.presentation.AMAgreementsResponse;
//import com.homeunion.view.rest.presentation.AMAgreementsResponseObj;
//import com.homeunion.view.rest.presentation.BaseRepresentation;
//import com.homeunion.view.rest.presentation.ViewMapper;
//
//@RestController
//// @Api (value="Expenses API",description="API to perform CRUD operations on
//// Expenses")
//@RequestMapping({"/api/v1/AMAgreements", "/AMAgreements"})
//public class AMAgreementsController {
//
//	private static final Logger LOG = LoggerFactory
//			.getLogger(AMAgreementsController.class);
//
//	@Autowired
//	private AMAgreementsService agreementsService;
//
//	@Autowired
//	private AssetsService assetsService;
//
//	@Autowired
//	CodeListService codeListService;
//
//	@Autowired
//	private DocumentService documentService;
//
//	@Autowired
//	private LeaseService leaseService;
//
//	@Autowired
//	private ViewMapper<AMAgreementsRequestDto> mapAMRequstToDto;
//
//	@Autowired
//	private ViewMapper<AMAPackageInfoResponse> mapAMInforResponse;
//
//	@RequestMapping(value = "/{assetId}", method = RequestMethod.GET)
//	public ResponseEntity<AMAgreementsResponse> getAMAgreements(
//			@PathVariable String assetId) {
//		LOG.info("Request to fetchAllAMAgreements:: assetId:{0}", assetId);
//
//		ResponseEntity<AMAgreementsResponse> response = null;
//		AMAgreementsResponse agreementsResponse = new AMAgreementsResponse();
//		List<AMAgreementsResponseObj> agreementResponseObjList = new ArrayList<AMAgreementsResponseObj>();
//		List<AMAgreements> agreements = new ArrayList<AMAgreements>();
//
//		Integer object = codeListService.getCodeByCodeGroupAndCodeDisplay(
//				CommonConstants.ASSET_TYPE_GROUP, CommonConstants.ASSET_TYPE)
//				.getId();
//		Integer subObject = codeListService.getCodeByCodeGroupAndCodeDisplay(
//				CommonConstants.AMAGREEMENT_OBJECT_GROUP,
//				CommonConstants.AMAGREEMENT_OBJECT).getId();
//
//		try {
//			agreements = agreementsService.fetchAllAMAgreements(assetId);
//			Asset asset = assetsService.fetchSingleAssetById(assetId);
//			BigDecimal purchasePrice = asset.getInvestment().getPurchasePrice();
//			BigDecimal rent = null;
//			List<Lease> leases = leaseService.getLeasesByAssetId(assetId);// .get(0).getMonthlyRent();
//			if (!NullCheckUtil.isEmpty(leases)) {
//				if (!NullCheckUtil.isEmpty(leases.get(0).getMonthlyRent())) {
//					rent = leases.get(0).getMonthlyRent();
//				}
//			}
//
//			for (AMAgreements singleAgreement : agreements) {
//				AMAgreementsResponseObj agreementResponseObj = new AMAgreementsResponseObj();
//				agreementResponseObj.setObject(object);
//				agreementResponseObj.setSubObject(subObject);
//				if (!NullCheckUtil.isEmpty(singleAgreement.getAgreementsId())) {
//					agreementResponseObj
//							.setAgreementsId(singleAgreement.getAgreementsId());
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getCreatedDate())) {
//					agreementResponseObj.setCreatedDate(
//							DateFormatter.getDateStringInUsFormat(
//									singleAgreement.getCreatedDate()));
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getSignedDate())) {
//					agreementResponseObj.setSignedDate(
//							DateFormatter.getDateStringInUsFormat(
//									singleAgreement.getSignedDate()));
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getStartDate())) {
//					agreementResponseObj
//							.setStartDate(DateFormatter.getDateStringInUsFormat(
//									singleAgreement.getStartDate()));
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getEndDate())) {
//					agreementResponseObj
//							.setEndDate(DateFormatter.getDateStringInUsFormat(
//									singleAgreement.getEndDate()));
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getAssetAcuisitionFee())) {
//					agreementResponseObj.setAssetAcuisitionFee(
//							singleAgreement.getAssetAcuisitionFee());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getAssetManagementFee())) {
//					agreementResponseObj.setAssetManagementFee(
//							singleAgreement.getAssetManagementFee());
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getAcqWaivers())) {
//					agreementResponseObj
//							.setAcqWaivers(singleAgreement.getAcqWaivers());
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getMgmtWaivers())) {
//					agreementResponseObj
//							.setMgmtWaivers(singleAgreement.getMgmtWaivers());
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getStatus())) {
//					agreementResponseObj.setStatusType(
//							singleAgreement.getStatus().getCodeDisplay());
//					agreementResponseObj.setStatusTypeId(String
//							.valueOf(singleAgreement.getStatus().getId()));
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getActions())) {
//					agreementResponseObj
//							.setActions(singleAgreement.getActions());
//				}
//
//				if (!NullCheckUtil.isEmpty(
//						singleAgreement.getIsAssetAcquisitionFeePercent())) {
//					agreementResponseObj.setIsAssetAcquisitionFeePercent(
//							singleAgreement.getIsAssetAcquisitionFeePercent());
//				}
//
//				if (!NullCheckUtil.isEmpty(
//						singleAgreement.getIsAssetManagementFeePercent())) {
//					agreementResponseObj.setIsAssetManagementFeePercent(
//							singleAgreement.getIsAssetManagementFeePercent());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getIsAMFeePercentOfRent())) {
//					agreementResponseObj.setIsAMFeePercentOfRent(
//							singleAgreement.getIsAMFeePercentOfRent());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getDiscountedAAFee())) {
//					agreementResponseObj.setDiscountedAAFee(
//							singleAgreement.getDiscountedAAFee());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getDiscountedAMFee())) {
//					agreementResponseObj.setDiscountedAMFee(
//							singleAgreement.getDiscountedAMFee());
//				}
//
//				if (!NullCheckUtil.isEmpty(
//						singleAgreement.getIsPropertyManagementFeePercent())) {
//					agreementResponseObj
//							.setIsPropertyManagementFeePercent(singleAgreement
//									.getIsPropertyManagementFeePercent());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getPropertyManagementFee())) {
//					agreementResponseObj.setPropertyManagementFee(
//							singleAgreement.getPropertyManagementFee());
//				}
//
//				// if (!NullCheckUtil.isEmpty(singleAgreement.getLeasingFee()))
//				// {
//				// agreementResponseObj
//				// .setLeasingFee(singleAgreement.getLeasingFee());
//				// }
//
//				if (CommonConstants.CHAR_YES
//						.equals(singleAgreement.getIsLeasePercent())) {
//					agreementResponseObj
//							.setLeasingFee(singleAgreement.getLeasingFee());
//				} else if (!NullCheckUtil
//						.isEmpty(singleAgreement.getLeasingFee())) {
//					agreementResponseObj.setLeasingFeeAmount(
//							singleAgreement.getLeasingFee().toString());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getIsLeasePercent())) {
//					agreementResponseObj.setIsLeasePercent(
//							singleAgreement.getIsLeasePercent());
//				}
//
//				if (CommonConstants.CHAR_YES
//						.equals(singleAgreement.getIsReleaseFeePercnt())) {
//					agreementResponseObj
//							.setReLeasingFee(singleAgreement.getReLeasingFee());
//				} else if (!NullCheckUtil
//						.isEmpty(singleAgreement.getReLeasingFee())) {
//					agreementResponseObj.setReLeasingFeeAmount(
//							singleAgreement.getReLeasingFee().toString());
//				}
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getIsReleaseFeePercnt())) {
//					agreementResponseObj.setIsReLeaseFeePercent(
//							singleAgreement.getIsReleaseFeePercnt());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getMaintenanceAmount())) {
//					agreementResponseObj.setMaintenanceAmount(
//							singleAgreement.getMaintenanceAmount());
//				}
//
//				if (!NullCheckUtil
//						.isEmpty(singleAgreement.getProvisionBilled())) {
//					agreementResponseObj.setProvisionBilled(
//							singleAgreement.getProvisionBilled());
//				}
//
//				if (!NullCheckUtil.isEmpty(singleAgreement.getIsAutoRenew())) {
//					agreementResponseObj
//							.setIsAutoRenew(singleAgreement.getIsAutoRenew());
//				}
//
//				agreementResponseObjList.add(agreementResponseObj);
//			}
//			agreementsResponse.setAgreementsResponses(agreementResponseObjList);
//			agreementsResponse.setPurchasePrice(purchasePrice);
//			agreementsResponse.setRent(rent);
//		} catch (Exception e) {
//			LOG.error("Error while fetching AMAgreements for the asset id: "
//					+ assetId + e.getMessage(), e);
//			response = new ResponseEntity<AMAgreementsResponse>(
//					agreementsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//			return response;
//		}
//		response = new ResponseEntity<AMAgreementsResponse>(agreementsResponse,
//				HttpStatus.OK);
//		return response;
//
//	}
//
//	@RequestMapping(value = "/editAMAgreements", method = RequestMethod.POST)
//	public ResponseEntity<BaseRepresentation> editOrAddAMAgreement(
//			AMAgreementsRequest request) {
//		LOG.info("Request to Update/Add AMAgreement for AMAgreementsId:{0}",
//				request.getAgreementsId());
//
//		BaseRepresentation response = new BaseRepresentation();
//		ResponseEntity<BaseRepresentation> finalResponse = null;
//
//		AMAgreementsRequestDto requestDto = mapAMRequstToDto.map(request,
//				AMAgreementsRequestDto.class);
//
//		try {
//
//			AMAgreements aMAgreementsSaved = agreementsService
//					.editOrAddAMAgreement(requestDto);
//			response.setMessage(
//					"Successfully added/updated the AMA Agreements.");
//			finalResponse = new ResponseEntity<BaseRepresentation>(response,
//					HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Error while saving AMAgreement: {},{}", e.getMessage(),
//					e);
//			response.setMessage(e.getMessage() != null
//					? e.getMessage()
//					: "Error occured while adding/updating AMA Agreements.");
//			finalResponse = new ResponseEntity<BaseRepresentation>(response,
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return finalResponse;
//	}
//
//	@RequestMapping(value = "/deleteAMAgreement/{agreementId}", method = RequestMethod.DELETE)
//	public ResponseEntity<BaseRepresentation> deleteAMAgreement(
//			@PathVariable String agreementId) {
//		BaseRepresentation response = new BaseRepresentation();
//		ResponseEntity<BaseRepresentation> finalResponse = null;
//		if (agreementsService.deleteAMAgreement(agreementId)) {
//			finalResponse = new ResponseEntity<BaseRepresentation>(response,
//					HttpStatus.OK);
//		} else {
//			finalResponse = new ResponseEntity<BaseRepresentation>(response,
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return finalResponse;
//
//	}
//
//	@RequestMapping(value = "/uploadDocuments", method = RequestMethod.POST)
//	public List<String> uploadAMDocument(
//			AMAgreementDocumentRequest docRequest) {
//		LOG.info("uploadAMDocument, request dto is {} ,", docRequest);
//		List<String> uploadedDocIds = new ArrayList<String>();
//		if (docRequest.getDocument() != null) {
//			for (CommonsMultipartFile singleDoc : docRequest.getDocument()) {
//				Map<String, Object> docMap = new HashMap<String, Object>();
//
//				docMap.put("docType",
//						Integer.valueOf(docRequest.getAgreementDocTypeId()));
//				docMap.put("document", singleDoc);
//				docMap.put("object", CommonConstants.ASSET_TYPE); // asset
//				docMap.put("objectId", docRequest.getAssetId());
//				docMap.put("documentFolder", CommonConstants.OTHER_DOCUMENTS);
//				LOG.info(
//						"uploadAMDocument, calling upload document method with request map {} ,",
//						docMap);
//				Document docUploaded = documentService.uploadDocument(docMap);
//				uploadedDocIds.add(docUploaded.getDocumentId());
//			}
//		}
//
//		return uploadedDocIds;
//	}
//
//	@RequestMapping(value = "fetchAmaPckgInfo/{object}/{objectId}", method = RequestMethod.GET)
//	public ResponseEntity<AMAPackageInfoResponse> getAmaPackageInfo(
//			@PathVariable String object, @PathVariable String objectId) {
//		AMAPackageInfoResponse amaResponse = null;
//		AMAPackageInfoDTO responseDto = null;
//		ResponseEntity<AMAPackageInfoResponse> response = null;
//		try {
//			responseDto = agreementsService.getAmaPackgInfo(object, objectId);
//			amaResponse = mapAMInforResponse.map(responseDto,
//					AMAPackageInfoResponse.class);
//			amaResponse.setMessage("Successfully fetched the AMA Package Info");
//			amaResponse.setStatusCode(HttpStatus.OK.toString());
//			response = new ResponseEntity<AMAPackageInfoResponse>(amaResponse,
//					HttpStatus.OK);
//		} catch (Exception ex) {
//			LOG.error(
//					"Fail to fetch the AMA Package info for object {} and objectId {}",
//					object, objectId);
//			amaResponse.setMessage("Error in fetching the AMA Package Info");
//			amaResponse
//					.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//			response = new ResponseEntity<AMAPackageInfoResponse>(amaResponse,
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return response;
//
//	}
//
//}
