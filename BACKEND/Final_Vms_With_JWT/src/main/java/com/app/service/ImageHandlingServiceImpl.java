package com.app.service;


import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.app.dto.ApiResponse;
import java.util.Optional;
import com.app.entity.Cars;
import com.app.repository.CarsRepository;

@Service
@Transactional
public class ImageHandlingServiceImpl implements ImageHandlingService {

    @Autowired
    private CarsRepository carRepo;

//    @Override
//    public ApiResponse uploadImage(Long carId, MultipartFile image) throws IOException {
//        Cars car = carRepo.findById(carId).orElseThrow();
//        car.setImage(image.getBytes());
//        carRepo.save(car);
//        return new ApiResponse("Image Added Successfully");
//    }

    
    @Override
    public ApiResponse uploadImage(Long carId, MultipartFile image) throws IOException {
        Cars car = carRepo.findById(carId).orElseThrow();

        // Convert the MultipartFile to a byte array
        byte[] imageBytes = image.getBytes();

        // Encode the byte array as a base64 string
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Set the base64 encoded image string to the car's 'image' field
        car.setImage(base64Image);

        // Save the car object
        carRepo.save(car);

        return new ApiResponse("Image Added Successfully");
    }
    
//    @Override
//    public byte[] downloadImage(Long carId) throws IOException {
//        Cars car = carRepo.findById(carId).orElseThrow();
//        return car.getImage();
//    }
    
    
    public byte[] downloadImage(Long carId) throws IOException {
        Optional<Cars> optionalCar = carRepo.findById(carId);
        if (optionalCar.isPresent()) {
            Cars car = optionalCar.get();
            String base64Image = car.getImage();

            // Decode the base64 string to a byte array
            return Base64.getDecoder().decode(base64Image);
        } else {
            throw null;
        }
    }

}
