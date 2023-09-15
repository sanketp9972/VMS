import React, { useEffect, useState } from "react";

import HeroSlider from "../components/UI/HeroSlider";
import Helmet from "../components/Helmet/Helmet";

import { Container, Row, Col } from "reactstrap";
import AboutSection from "../components/UI/AboutSection";
import CarItem from "../components/UI/CarItem";
import axios from "axios";
import { createUrl, log } from '../utils/utils';

const Home = () => {

   const [cars, setCars] = useState([]); 

  useEffect( () => {
    loadCars();
  }, []);

  const loadCars = () => {
    const url = createUrl('/cars');
    axios.get(url)
    .then(function (response) {
      setCars(response.data);
      log(response);
    })
    .catch(function (error) {
      
      log(error);
    })
    .finally(function () {
     
    });
  }



  return (
    <Helmet title="Home">
      {/* ============= hero section =========== */}
      <section className="p-0 hero__slider-section">
        <HeroSlider />
      </section>
      {/* =========== about section ================ */}
      <AboutSection />
      {/* =========== car offer section ============= */}
      <section>
        <Container>
           <Row>
            <Col lg="12" className="text-center mb-5">
              <h6 className="section__subtitle">Come with</h6>
              <h2 className="section__title">Hot Offers</h2>
            </Col>

            {cars.slice(0, 6).map((item) => (
              <CarItem item={item} key={item.id} />
            ))}
          </Row>
        </Container>
      </section>
      
    </Helmet>
  );
};

export default Home;
